# Slicense

Slicense is an information security challenge in the Reversing category, and was presented to participants of [KAF CTF 2019](https://ctf.kipodafterfree.com)

## Challenge story

Break the egg!

## Challenge exploit

A local key verification is present, making it easy for an attacker to reverse it and find a matching license key.

## Challenge solution

Full app reversing

## Building and installing

[Clone](https://github.com/NadavTasher/2019-Slicense/archive/master.zip) the repository, then type the following command to build the container:
```bash
./certificate.sh
./build.sh
```

To run the challenge, execute the following command:
```bash
docker run --rm -d -p 3579:80 slicense
```

## Flag

Flag is:
```flagscript
KAF{s0_many_lay3rs_399_crack3d}
```

## License
[MIT License](https://choosealicense.com/licenses/mit/)