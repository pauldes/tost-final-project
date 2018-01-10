from surprise import Reader, Dataset
from surprise import KNNBasic, evaluate
import csv

reader = Reader(line_format='user item rating',
                sep=';',
                rating_scale=(-1,3))

print ("Loading data...")
data = Dataset.load_from_file('./tost/u.data', reader=reader)
data.split(n_folds=3)
print(">OK\n")

#Build the trainset
trainset = data.build_full_trainset()

#Define similarities options
#sim_options = {
#    'name': 'cosine',
#    'user_based': True  # compute  similarities between items
#}

print ("Training data...")
#Build an algo, and train it
algo = KNNBasic()
algo.train(trainset)
print("> OK\n")

#Get predicitions
print("Predictions :")
ratings = vars(data).get('raw_ratings')

with open('tost/u.results', "wb") as csv_file:
    writer = csv.writer(csv_file, delimiter=';')

    with open('tost/u.user', 'rb') as users:
        user_reader = csv.reader(users, delimiter=";")
        for user in user_reader:
            uid=user[0]

            with open('tost/u.item', 'rb') as items:
                item_reader = csv.reader(items, delimiter=";")
                for item in item_reader:
                    iid=item[0]

                    rating = 0
                    user_rating = False             #true if user has already rated the place
                    for tup in ratings:
                        if (tup[0]==uid and tup[1]==iid):
                            rating = tup[2]
                            user_rating = True

                    pred = algo.predict(uid, iid, rating, verbose=True)
                    pred_rating = vars(pred)["est"]         #get the value of the estimated rating

                    #Write results in u.results file
                    line = [uid, iid, pred_rating, user_rating]
                    writer.writerow(line)

