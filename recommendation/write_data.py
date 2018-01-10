import MySQLdb
import csv

db = MySQLdb.connect(host="127.0.0.1",    # your host, usually localhost
                     user="root",         # your username
                     passwd="",  # your password
                     db="tost_db")        # name of the data base

# you must create a Cursor object. It will let
#  you execute all the queries you need
cur = db.cursor()

# TODO: Make predictions according to date, location, tags ?
# for now, genius result is based only on the higher estimated rating

with open('tost/u.user', 'rb') as users:
    user_reader = csv.reader(users, delimiter=";")
    for user in user_reader:
        uid=user[0]
        unrated_item=[]

        with open('tost/u.results', "rb") as csv_file:
            reader = csv.reader(csv_file, delimiter=';')
            for line in reader:
                if (line[0] == uid and line[3]=='False'):
                    unrated_item.append(line)

        unrated_item=sorted(unrated_item, key=lambda x: x[2], reverse=True)
        sorted_items=[i[1] for i in unrated_item]
        sorted_items=','.join(sorted_items)

        print "Sorted items tuple: ", unrated_item
        print "Sorted items list: ", sorted_items


        # Extract id_user that are already in database
        cur.execute("SELECT id, id_user FROM user_recommendations WHERE id_user="+uid)
        cursor = cur.fetchall()
        if(len(cursor)==0):
            print('Creating new entry')
            insert_est = (
                "INSERT INTO user_recommendations (id_user, id_places) "
                "VALUES (%s, %s)"
            )
            data = (uid, sorted_items)
            cur.execute(insert_est, data)

        else:
            print('Updating data')
            id = cursor[0][0]
            update_est = (
                "UPDATE user_recommendations "
                "SET id_user=%s, id_places=%s "
                "WHERE id=%s"
            )
            data = (uid, sorted_items,id)
            cur.execute(update_est, data)

db.close()