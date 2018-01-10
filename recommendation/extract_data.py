import MySQLdb
import csv

db = MySQLdb.connect(host="127.0.0.1",    # your host, usually localhost
                     user="root",         # your username
                     passwd="",  # your password
                     db="tost_db")        # name of the data base

# you must create a Cursor object. It will let
#  you execute all the queries you need
cur = db.cursor()


cur.execute("SELECT * FROM places")
# print the rows and write them in u.user
with open('tost/u.item', "wb") as csv_file:
    writer = csv.writer(csv_file, delimiter=';')
    for row in cur.fetchall():
        print('Place # ',row[0],' is ',row[1])
        writer.writerow([row[0], row[1]])


print('----')

cur.execute("SELECT * FROM userx")
# print the rows and write them in u.data
with open('tost/u.user', "wb") as csv_file:
    writer = csv.writer(csv_file, delimiter=';')
    for row in cur.fetchall():
        print('User #',row[0],' is ',row[1])
        writer.writerow([row[0], row[1]])

print('----')

cur.execute("SELECT * FROM user_liked_places")
# print the rows and write the data in u.data
with open('tost/u.data', "wb") as csv_file:
    writer = csv.writer(csv_file, delimiter=';')
    for row in cur.fetchall():
        print('User #',row[1],' liked place #',row[2],' with score ',row[3])
        writer.writerow([row[1], row[2], row[3]])

db.close()