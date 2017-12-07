from surprise import Reader, Dataset
from surprise import accuracy
from surprise import SVD, evaluate

reader = Reader(line_format='user item rating',
                sep=';',
                rating_scale=(0,1))

data = Dataset.load_from_file('./tost/u.data', reader=reader)
data.split(n_folds=3)

algo = SVD()
#evaluate(algo, data, measures=['RMSE', 'MAE'])

#Retrieve the trainset : training on the whole dataset
for trainset, testset in data.folds():
    algo.train(trainset)
    predictions = algo.test(testset)
    rmse = accuracy.rmse(predictions, verbose=True)

