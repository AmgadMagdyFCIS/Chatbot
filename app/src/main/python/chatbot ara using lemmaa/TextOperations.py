from __future__ import unicode_literals   
import re
import nltk
import string                   
import pyarabic.araby as araby 
import itertools   

from nltk.stem.arlstem import ARLSTem
stemmer = ARLSTem()


class DataOperations:
  def stem(self,word):
      word = stemmer.stem(word)
      return word

  def Tokenize(self,text):
      text = text.split(" ")
      return text

    
  def synonym_mapper(self,text):
    new_text = ""
    #convert all english words to lowercase
    for char in text:
      index = text.index(char)
      match = re.match(r"[a-zA-Z]+|[a-zA-Z1-9]+",char)
      if match:
        new_text += text[index].lower()
      else:
        new_text += text[index]
    # copy newtext to ntext by putting space between words   same as copy
    n_text = ''.join(new_text)                      


    words_to_replace = {
        'support asu':'سبورت',
         'email':'ايميل'
,        'support':'سبورت','support-asu':'سبورت','support_asu':'سبورت',
        'صبورت':'سبورت',
        'robotech-asu':'روبوتيك','robotech_asu':'روبوتيك','robotech':'روبوتيك','robotech asu':'روبوتيك',
        'robotics':'روبوتيك','osc':'اواسسي','i-club':'ايكلوب','i_club':'ايكلوب','i club':'ايكلوب',
        'msp tech club':'مسب',
        'ام اس بي':'مسب','humans of fcis':'هيومان','humans-of-fcis':'هيومان','human of fcis':'هيومان','human-of-fcis':'هيومان',
        'student activities':'انشطه الطلابيه','committees':'انشطه الطلابيه','activities':'انشطه الطلابيه',
        'content':'محتوي','acm':'ايسيام','bioinformatics':'بايو','bio':'بايو','نظم المعلومات الحيوية':'بايو',
        'multimedia':'مالتيميديا','الوسائط المتعددة':'مالتيميديا','swe':'هندسه البرمجيات','software engineering':'هندسة البرمجيات',
        'سوفتوير انجينيرينج':'هندسه البرمجيات','is':'نظم المعلومات','اي اس':'نظم المعلومات','information systems':'نظم المعلومات',
        'information system':'نظم المعلومات','computer science':'علوم الحاسب','cs':'علوم الحاسب','سي اس':'علوم الحاسب',
        'scientific computing':'الحسابات العلميه','sc':'الحسابات العلميه','اس سي':'الحسابات العلميه','سيستمز':'نظم الحاسبات',
        'csys':'نظم الحاسبات','computer systems':'نظم الحاسبات','ai':'الذكاء الاصطناعي','الذكاء الصناعي':'الذكاء الاصطناعي',
        'ايه اي':'الذكاء الاصطناعي','artifitial intelligence':'الذكاء الاصطناعي','ذكاء اصطناعي':'الذكاء الاصطناعي',
        'الامن السيبراني':'امن المعلومات','cyber':'امن المعلومات','cyber security':"امن المعلومات",'physics':'الفيزياء',
        'فيزيا':'الفيزياء','الفيزيا':'الفيزياء','فيزياء':'الفيزياء','hp':'اتش بي','department':'قسم','departments':'اقسام',
        'faculty':'الكليه'
        ,'واحد':'1'
        ,'اتنين':'2'
        ,'اثنان':'2'
        ,'اثنين':'2'
        ,'تلاته':'3'
        ,'ثلاثه':'3'
,'اربعه'  :'4'
,
        'خمسه':'5'
        ,'سته':'6',
        'سبعه':'7'
    }

    for i in range(len(n_text)):
      for j in range(i+1,len(n_text)+1):
        word = n_text[i:j]
        if word in words_to_replace.keys():
          #print(word)
          value = words_to_replace.get(word)
          n_text = n_text[:i] + value + n_text[j:]
          #print(value)
        
    
    #print(n_text)

    return n_text
  
  def remove_repeated_words(self,text):
    new_text = []
    for word in text:
      if word not in new_text:
        new_text.append(word)
    return new_text

  def remove_diacraties(self,text):
    text = araby.strip_diacritics(text)
    return text
  
  def remove_punctuation(self,text):
    text = text.translate(str.maketrans('', '', '''[]?؟!@#$%^&*)(><><{}`÷×؛_ـ،/:".,'~¦+|!”…“–ـ'''))
    return text

  def remove_repeating_chars(self,text):
      text = ''.join(char for char, _ in itertools.groupby(text))
      return text
  
  #returns a copy of the string by removing both the leading and the trailing characters (based on the string argument passed).
  def remove_spaces(self,text):                                       
    text = text.strip(" ")
    return text
      

  def normalize_chars(self,text):
    text = re.sub(r'[إأٱآا]', r'ا', text)
    text = re.sub(r"ى", r"ي", text)
    text = re.sub(r"ة", r"ه", text)
    text = re.sub(r"ؤ",r"ئ",text) 
    return text
  
  def preprocess_text(self,text):
    new_text = DataOperations.remove_spaces(self,text)
    new_text = DataOperations.normalize_chars(self,new_text)
    new_text = DataOperations.remove_repeating_chars(self,new_text)
    new_text = DataOperations.remove_punctuation(self,new_text)
    new_text = DataOperations.remove_diacraties(self,new_text)
    new_text = DataOperations.synonym_mapper(self,new_text)
    new_text = DataOperations.Tokenize(self,str(new_text))
    new_text = DataOperations.remove_repeated_words(self,new_text)
    sentence=[]
    for word in new_text:
        sentence.append(DataOperations.stem(self,word))
    return list(set(sentence))

#sentence="عاوز ايميل أآإٱ راااائع محمد ه?اشم  email  سته  ولوالدِينَا عاوز ثم!   human of fcis "
#d=DataOperations()
#print(d.preprocess_text(sentence))
