import csv

#get a proposition for a specified user
uid = raw_input("Please enter user ID: ")

# TODO: Make predictions according to date, location, tags ?
# for now, genius result is based only on the higher estimated rating
unrated_item=[]

with open('tost/u.results', "rb") as csv_file:
    reader = csv.reader(csv_file, delimiter=';')
    for line in reader:
        if (line[0] == uid and line[3]=='False'):
            print line
            unrated_item.append(line)

unrated_item=sorted(unrated_item, key=lambda x: x[2])
sorted_items=[i[1] for i in unrated_item]

print "Sorted items tuple: ", unrated_item
print "Sorted items list: ", sorted_items



