import csv

#get a proposition for a specified user
uid = raw_input("Please enter user ID: ")

# TODO: Make predictions according to date, location, tags ?
# for now, genius result is based only on the higher estimated rating
max_rating = 0

with open('tost/u.results', "rb") as csv_file:
    reader = csv.reader(csv_file, delimiter=';')
    for line in reader:
        if (line[0] == uid and line[3]=='False'):
            if line[2] > max_rating:
                print line[2], max_rating
                max_rating = line[2]
                genius_res = line

print genius_res
