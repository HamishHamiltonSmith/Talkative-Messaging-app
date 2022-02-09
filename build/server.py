import os


#Set up directory structure

print("[+] Ready to set up directorys, any existing talkative infuastructure shall be overwritten...")
x = str(input("Press enter to begin: "))

paths = ["/usr/share/Talkative","/usr/share/Talkative/server/users","/usr/share/Talkative/server/users/DefaultUser1","/usr/share/Talkative/server/Chats","/usr/share/Talkative/server/src"]

for p in paths:
    if os.path.exists(p):
        if paths.index(p) == 0:
            continue
        else:
            print(f"[+] Found existing directory: {path}, overwriting")
            os.system(f"rm -r {p}")
            os.system(f"mkdir {p}")
    else:
        os.system(f"mkdir {p}")


#Set up directory structure

print("[+] Completed directory setup, ready to begin file setup")
x = str(input("Press enter to begin: "))


os.system("mv ~/Downloads/Talkative/src/server/* /usr/share/Talkative/server/src")
os.system("touch /usr/share/Talkative/src/server/users/DefaultUser1/user_chats.txt")


