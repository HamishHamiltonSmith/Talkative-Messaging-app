#Run as root if build fails due to permissions

#Sets up directorys:
# /usr/share/Talkative + client/UserData
# /usr/share/Talkative + client/Src

import os

print("[+] Ready to set up client, any existing talkative client infastructure will be replaced...")
x = str(input("Press enter to begin: "))

downloadPath = str(input("Please specify path to Talkative download file (eg:/home/user/Downloads): "))

paths = ["/usr/share/Talkative","/usr/share/Talkative/client","/usr/share/Talkative/client/UserData","/usr/share/Talkative/client/src"]

#Create directorys
for p in paths:
    if not os.path.exists(p):
        os.system(f"mkdir {p}")
    else:
        if paths.index(p) == 0:
            continue
        else:
            print(f"[+] Found existing directory: {p}, overwriting")
            os.system(f"rm -r {p}")
            os.system(f"mkdir {p}")


print("[+] Ready to set up files")
x = str(input("Press enter to begin: "))

#Transfer files and src dir
os.system("touch /usr/share/Talkative/client/UserData/user.txt")
f = open("/usr/share/Talkative/client/UserData/user.txt",'w')
f.write("DefaultUser1")
f.close()

os.system(f"mv {downloadPath}/Talkative/src/client/* /usr/share/Talkative/client/src")

