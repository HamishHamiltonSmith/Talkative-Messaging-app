import os


#Set up directory structure

print("[+] Ready to set up directorys, any existing talkative infuastructure shall be overwritten...")
x = str(input("Press enter to begin: "))

paths = ["/usr/share/Talkative","/usr/share/Talkative/Server/Users","/usr/share/Talkative/Server/Users/DefaultUser1","/usr/share/Talkative/Server/Chats","/usr/share/Talkative/Server/Src"]

for p in paths:
    if os.path.exists(p):
        print(f"[+] Found existing directory: {path}, overwriting")
        os.system(f"rm -r {p}")
        os.system(f"mkdir {p}")
    else:
        os.system(f"mkdir {p}")


#Set up directory structure

print("[+] Completed directory setup, ready to begin file setup")
x = str(input("Press enter to begin: "))


os.system("mv ~/Downloads/Talkative/Src/Server/* /usr/share/Talkative/Server/Src")
os.system("touch /usr/share/Talkative/Src/Server/Users/DefaultUser1/user_chats.txt")


