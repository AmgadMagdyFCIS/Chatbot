import json
import tensorflow as tf
import tflearn 
import random
import numpy as np
import nltk
from TextOperations import DataOperations
import os
nltk.download('punkt')



class Nlp_plus_ANN:
    ## Loading the data:
    dir_path = os.path.dirname(os.path.realpath(__file__))
    ## Loading the data:
    with open(dir_path + '/' + 'database.json',encoding="utf8")as datafile:
        intents =json.load(datafile)

    
    threshold=-99999999999999999999999999999999999999999999999999999999
    
    ## Data pre-processing:
    preprocess=DataOperations()
    words=[]
    classes=[]
    documents=[]
    
    words_stemed=[]
    

    for intent in intents['intents']:
      for pattern in intent['patterns']:
        w = preprocess.preprocess_text(pattern)
        if len(w)>0:
          words.extend(w)
        documents.append((w,intent['tag']))
        classes.append( intent['tag'] )
          
    #words_stemed=[stemmer.stem(w) for w in words if w not in ignore_words]
    words = sorted(list(set(words)))  
    #classes = sorted(list(set(classes)))
    
    #print(len(documents)," documents")
    #print(len(classes)," classes", classes)
    #print(len(words)," unique stemmed word", words)
    
    
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
    model.load(dir_path + '/' +'./model.tflearn')
    
    ## Predictions:
    
    
    
    ## Inference Functions:
    def clean_up_sentence(self,sentence):
        sentence_words = self.preprocess.preprocess_text(sentence)
        return sentence_words
    
    
    
    def bow(self,sentence,words,show_details=True):
        sentence_words = self.clean_up_sentence(sentence)
        bag=[]
        for w in words:
            if w in sentence_words:
              bag.append(1)
            else:
              bag.append(0)
        return(np.array(bag))
    
    def classify(self,sentence):
        results = self.model.predict([self.bow(sentence,self.words)])[0]
        #print("acc?",results)
        results = [ [i,r] for i,r in enumerate(results) if r>self.threshold ]
        #print("Main acc?",results)
        results.sort(key=lambda x: x[1],reverse = True)
        #print("Main acc sorted?",results)
        return_list=[]
        for r in results:
            #print("HHH",r[0],r[1])
            return_list.append( ( self.classes[r[0]],r[1]   ) )
        return return_list
    
    
    def response(self,sentence,userid=0,show_details=False):
        results = self.classify(sentence)
        if results:
            while results:
                for i in self.intents['intents']:
                    if i['tag'] == results[0][0]:
                        return (i['responses'])
    
                results.pop(0)
    

