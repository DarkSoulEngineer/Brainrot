import os
import time
import psutil
import subprocess
import logging
import pygetwindow as gw
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

# Configure logging
logging.basicConfig(filename='usb_monitor.log', level=logging.INFO, format='%(asctime)s - %(message)s')

# Set to keep track of existing USB drives
existing_drives = set()
# Dictionary to keep track of running executables and their paths
running_executables = {}

# Function to get all current USB drives
def get_current_usb_drives():
    usb_drives = set()
    for drive in psutil.disk_partitions(all=False):
        if 'removable' in drive.opts:  # Check if the drive is removable (USB)
            usb_drives.add(drive.mountpoint)
    return usb_drives

# Function to check if a specific executable is already running
def is_executable_running(executable_name):
    for proc in psutil.process_iter(['name']):
        if proc.info['name'].lower() == executable_name.lower():
            return True
    return False

# Function to run executables from a USB drive
def run_executables_from_drive(drive_path):
    logging.info(f"Checking for .exe files in: {drive_path}")
    for filename in os.listdir(drive_path):
        if filename.endswith(".exe"):
            executable_path = os.path.join(drive_path, filename)
            if os.path.isfile(executable_path):  # Check if the file exists
                if not is_executable_running(filename):  # If not running, start it
                    logging.info(f"Running {executable_path}...")
                    try:
                        process = subprocess.Popen(executable_path)  # Use Popen to avoid blocking
                        running_executables[filename] = executable_path  # Track the executable's path
                    except FileNotFoundError:
                        logging.error(f"File not found: {executable_path}")
                    except subprocess.CalledProcessError as e:
                        logging.error(f"Failed to run {filename}: {e}")
                else:
                    logging.info(f"{filename} is already running. Doing nothing.")
            else:
                logging.error(f"Executable not found: {executable_path}")

# Function to close all File Explorer windows
def close_file_explorer():
    logging.info("Closing all open File Explorer windows...")
    for window in gw.getWindowsWithTitle('File Explorer'):
        window.close()
        logging.info(f"Closed File Explorer window: {window.title}")

# Function to monitor and reopen executables if closed
def monitor_executables():
    while True:
        time.sleep(1)  # Check every second
        for filename, executable_path in list(running_executables.items()):
            # Check if the process is still running
            for proc in psutil.process_iter(['name']):
                if proc.info['name'].lower() == filename.lower():
                    break
            else:  # Executable is not running
                logging.info(f"{filename} has been closed. Restarting...")
                try:
                    process = subprocess.Popen(executable_path)  # Start the executable again
                    running_executables[filename] = executable_path  # Update the tracked process
                except FileNotFoundError:
                    logging.error(f"File not found for restarting: {executable_path}")

# Event handler for monitoring drives
class USBEventHandler(FileSystemEventHandler):
    def on_created(self, event):
        if event.is_directory:
            logging.info(f"New USB drive added: {event.src_path}")
            close_file_explorer()  # Close File Explorer before running
            run_executables_from_drive(event.src_path)

# Start monitoring the system
def start_monitoring():
    global existing_drives
    existing_drives = get_current_usb_drives()
    
    # Run executables from currently connected USB drives
    for drive in existing_drives:
        logging.info(f"Auto-running executables from already connected USB drive: {drive}")
        close_file_explorer()  # Close File Explorer before running
        run_executables_from_drive(drive)

    event_handler = USBEventHandler()
    observer = Observer()
    
    # Monitor all drive letters (A-Z)
    for letter in range(ord('A'), ord('Z') + 1):
        drive = f"{chr(letter)}:\\"
        if os.path.exists(drive):  # Check if the drive exists
            observer.schedule(event_handler, path=drive, recursive=False)

    observer.start()
    logging.info("Monitoring for new USB drives...")

    try:
        while True:
            time.sleep(2)  # Keep the script running, check every 2 seconds
            current_drives = get_current_usb_drives()
            new_drives = current_drives - existing_drives
            if new_drives:
                for drive in new_drives:
                    logging.info(f"New USB drive detected: {drive}")
                    close_file_explorer()  # Close File Explorer before running
                    run_executables_from_drive(drive)
                existing_drives.update(new_drives)
                
            # Monitor running executables
            monitor_executables()
            
            # Check for reconnected drives
            disconnected_drives = existing_drives - current_drives
            if disconnected_drives:
                for drive in disconnected_drives:
                    logging.info(f"USB drive disconnected: {drive}")
                existing_drives.difference_update(disconnected_drives)  # Update existing drives set
    except KeyboardInterrupt:
        observer.stop()
        logging.info("Stopped monitoring.")
        exit()
        
    observer.join()

if __name__ == "__main__":
    start_monitoring()
