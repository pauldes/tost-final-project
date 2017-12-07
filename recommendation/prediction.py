from surprise import Reader, Dataset
from surprise import KNNBasic, evaluate

reader = Reader(line_format='user item rating',
                sep=';',
                rating_scale=(-1,1))

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

print("Predictions :")
#get a prediction for a specific user and item
uid = str(1)
iid = str(4)
pred = algo.predict(uid, iid, r_ui=0, verbose=True)
