import json
#import pickle
import tensorflow as tf
import tflearn 
import random
import numpy as np
#import random
import nltk
#import re
nltk.download('punkt')
from nltk.stem.arlstem import ARLSTem

from nltk.stem import WordNetLemmatizer
nltk.download('wordnet')
lemma=WordNetLemmatizer()


stemmer = ARLSTem()


## Loading the data:
with open('intents_withnorepeatDoctor.json',encoding="utf8")as datafile:
  intents =json.load(datafile)


## Data pre-processing:
words=[]
classes=[]
documents=[]
ignore_words = ["من", "فى", "الي", "علي", "عن", "حتي", "مذ", "منذ", "و", "الا", "او", "ام", "ثم", "بل", "لكن",
                                  "يخويا"," زميلي", " يسطا", "يستا", "صاحبي","يا","!","؟", "كل" , "متى" , "يوم"]
words_stemed=[]


for intent in intents['intents']:
  for pattern in intent['patterns']:
    w = nltk.word_tokenize(pattern)
    trying_list=[]
    [trying_list.append(lemma.lemmatize(item))for item in w if lemma.lemmatize(item) not in trying_list]
    if len(trying_list)>0:
      words.extend(trying_list)
    documents.append((trying_list,intent['tag']))
    classes.append(intent['tag'])
      
#words_stemed=[stemmer.stem(w) for w in words if w not in ignore_words]
words = sorted(list(set(words)))  
#classes = sorted(list(set(classes)))

print(len(documents)," documents")
print(len(classes)," classes", classes)
print(len(words)," unique stemmed word", words)


## Converting data into vectors:
training=[]
for doc in documents:
    bag=[]

    pattern_words = doc[0]

    #pattern_words=[stemmer.stem(word) for word in pattern_words]
    for w in words:
        if w in pattern_words:
          bag.append(1)
        else:
          bag.append(0)
    output_raw = [0]*len(classes)
    output_raw[classes.index(doc[1])]=1
    training.append([bag, output_raw])
random.shuffle(training)
training = np.array(training)
train_x = list(training[:,0])
train_y = list(training[:,1])

## Building the model:
tf.compat.v1.reset_default_graph()
net=tflearn.input_data(shape=[None,len(train_x[0])])
net=tflearn.fully_connected(net,8)
net=tflearn.fully_connected(net,8)
net=tflearn.fully_connected(net, len(train_y[0]), activation='softmax') #len(trainy[0])
net=tflearn.regression(net)
model=tflearn.DNN(net,tensorboard_dir='tflearn_logs')
#model.fit(train_x,train_y,n_epoch=500,batch_size=8,show_metric=True)
#model.save('model.tflearn')
model.load('./model.tflearn')

## Predictions:



## Inference Functions:
def clean_up_sentence(sentence):
    sentence_words = nltk.word_tokenize(sentence)
    arafa=[]
    for word in sentence_words: 
      if word not in ignore_words:
          arafa.append(lemma.lemmatize(word)) 
    print ("hyyyyyyyyyyy",arafa)
    return list(set(arafa))


def bow(sentence,words,show_details=True):
    sentence_words = clean_up_sentence(sentence)
    bag=[]
    for w in words:
        if w in sentence_words:
          bag.append(1)
        else:
          bag.append(0)
    return(np.array(bag))

def classify(sentence):
    results = model.predict([bow(sentence,words)])[0]
    print("acc?",results)
    results = [ [i,r] for i,r in enumerate(results) if r>-99999999999999999999999999999999999999999999999999999999 ]
    print("Main acc?",results)
    results.sort(key=lambda x: x[1],reverse = True)
    print("Main acc sorted?",results)
    return_list=[]
    for r in results:
        print("HHH",r[0],r[1])
        return_list.append( (   classes[r[0]],r[1]   ) )
    return return_list


def response(sentence,userid=0,show_details=False):
    newSentence = EnglishToArabicIdioms(sentence)
    results = classify(newSentence)
    if results:
        while results:
            for i in intents['intents']:
                if i['tag'] == results[0][0]:
                    return (i['responses'][0])

            results.pop(0)

            
def EnglishToArabicIdioms(sentence) :
    words_to_replace = {'student activities': 'انشطه الطلابيه', 'committees':'انشطه الطلابيه',
                        'activities': 'انشطه الطلابيه', 'student communities': 'انشطه الطلابيه',
                        'communities': 'انشطه الطلابيه', 'activity': 'نشاط طلابي', 'content': 'محتوي',
                        'Acm':'ايسيام', 'acm':'ايسيام', 'ACM':'ايسيام','bioinformatics': 'بايو',
                        'bio': 'بايو', 'نظم المعلومات الحيوية' : 'بايو' , 'نظم المعلومات الحيويه' : 'بايو',
                        'مالتي':'مالتيميديا',  'multimedia':'مالتيميديا',  'Multimedia':'مالتيميديا',
                        'الوسائط المتعددة':'مالتيميديا', 'الوسائط المتعدده':'مالتيميديا',
                        'SWE':'هندسه البرمجيات',  'Software Engineering':'هندسه البرمجيات',
                        'سوفت وير':'هندسه البرمجيات',  'سوفتوير انجينيرينج':'هندسه البرمجيات',
                        'Swe':'هندسه البرمجيات',  'swe':'هندسه البرمجيات', 'اي اس':'نظم المعلومات',
                        'IS':'نظم المعلومات',  'Is':'نظم المعلومات', 'is':'نظم المعلومات',
                        'Information Systems':'نظم المعلومات',  'Information systems':'نظم المعلومات',
                        'information system':'نظم المعلومات',  'Information System':'نظم المعلومات',
                        'سي اس':'علوم الحاسب','CS':'علوم الحاسب',  'Cs':'علوم الحاسب', 'cs':'علوم الحاسب',
                        'Computer Science':'علوم الحاسب',  'computer science':'علوم الحاسب',
                        'Computer science':'علوم الحاسب',
                        'cS':'علوم الحاسب',
                         'اس سي':'الحسابات العلميه','SC':'الحسابات العلميه',  'Sc':'الحسابات العلميه',
                         'sC':'الحسابات العلميه','Scientific Computing':'الحسابات العلميه',
                         'Scientific computing':'الحسابات العلميه', 'scientific computing':'الحسابات العلميه',
                        'سيستمز':'نظم الحاسبات','Csys':'نظم الحاسبات',  'CSys':'نظم الحاسبات',
                         'CSYS':'نظم الحاسبات','Computer Systems':'نظم الحاسبات',
                         'Computer systems':'نظم الحاسبات', 'computer systems':'نظم الحاسبات',
                        'ذكاء اصطناعي':'الذكاء الاصطناعي', 'ai':'الذكاء الاصطناعي', 'AI':'الذكاء الاصطناعي',
                        'ايه اي':'الذكاء الاصطناعي',
                        'Ai':'الذكاء الاصطناعي', 'aI':'الذكاء الاصطناعي','الذكاء الصناعي':'الذكاء الاصطناعي',
                        'artifial intelligence':'الذكاء الاصطناعي', 'Artifial Intelligence':'الذكاء الاصطناعي',
                        'Artifial intelligence':'الذكاء الاصطناعي',
                        'الأمن السيبراني':'امن المعلومات',
                        'Cyber Security':'امن المعلومات', 'Cyber':'امن المعلومات', 'Security':'امن المعلومات',
                        'Cyber security':'امن المعلومات', 'cyber security':'امن المعلومات',
                         'الامن السيبراني':'امن المعلومات',
                         '1':'واحد',  '2':'اتنين',  'اثنين':'اتنين',  'اثنان':'اتنين',  '3':'تلاته', 
                         'ثلاثه':'تلاته',  '5':'خمسه',  'خمسة':'خمسه',  'ستة':'سته', 
                          '6':'سته', '7':'سبعه', 'سبعة':'سبعه', 'hp':'اتش بي',  'HP':'اتش بي', 
                        'Hp':'اتش بي',  'hP':'اتش بي', 'Physics':'الفيزياء', 'physics':'الفيزياء', 
                        'الفيزيا':'الفيزياء', 'فيزياء':'الفيزياء',  'فيزيا':'الفيزياء', 
                        'Department':'قسم',  'Departments':'اقسام','faculty':'الكليه'}
    # Iterate over all key-value pairs in dictionary 
    for key, value in words_to_replace.items():
    # Replace key character with value character in string
        sentence = sentence.replace(key, value)
    print(sentence)
    return sentence     


while(True):
    task=input("User: ")
    print("FCIS Freshman Bot: ",response(task))
     

