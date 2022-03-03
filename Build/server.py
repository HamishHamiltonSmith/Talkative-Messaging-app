import os


#Set up directory structure

print("[+] Ready to set up directorys, any existing talkative server infuastructure shall be overwritten...")
x = str(input("Press enter to begin: "))

downloadPath = str(input("Please specify path to Talkative download file (eg:/home/user/Downloads): "))

paths = ["/usr/share/Talkative","/usr/share/Talkative/server","/usr/share/Talkative/server/users","/usr/share/Talkative/server/users/DefaultUser1","/usr/share/Talkative/server/chats","/usr/share/Talkative/server/src"]

for p in paths:
    if os.path.exists(p):
        if paths.index(p) == 0:
            continue
        else:
            print(f"[+] Found existing directory: {p}, overwriting")
            os.system(f"rm -r {p}")
            os.system(f"mkdir {p}")
    else:
        os.system(f"mkdir {p}")


#Set up directory structure

print("[+] Completed directory setup, ready to begin file setup")
x = str(input("Press enter to begin: "))

try:
    os.system(f"mv {downloadPath}/Talkative/src/server/* /usr/share/Talkative/server/src")
except:
    print("Error transfering source files, Probably due to invalid download path")
    
os.system("touch /usr/share/Talkative/server/users/DefaultUser1/user_chats.txt")


