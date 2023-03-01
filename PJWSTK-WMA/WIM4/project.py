from keras.preprocessing.image import ImageDataGenerator
from keras.models import Sequential
from keras.layers.convolutional  import Conv2D, MaxPooling2D
from keras.layers.core import Activation, Dropout, Flatten, Dense
import os
import cv2
import tensorflow as tf
from keras.preprocessing import image
import numpy as np
from keras import backend as K

img_width, img_height = 150, 150

train_data_dir = 'data/train'
validation_data_dir = 'data/test'

for image_class in os.listdir(train_data_dir):
    for image in os.listdir(f'{train_data_dir}/{image_class}'):
        path = f'{train_data_dir}/{image_class}/{image}'
        im = cv2.imread(path)
        resized = cv2.resize(im, (img_width, img_height))
        os.remove(path)
        cv2.imwrite(path, resized)

for image_class in os.listdir(validation_data_dir):
    for image in os.listdir(f'{validation_data_dir}/{image_class}'):
        path = f'{validation_data_dir}/{image_class}/{image}'
        im = cv2.imread(path)
        resized = cv2.resize(im, (img_width, img_height))
        os.remove(path)
        cv2.imwrite(path, resized)


batch_size = 5
nb_train_samples = 58
nb_validation_samples = 3
epochs = 15


if K.image_data_format() == 'channels_first':
    input_shape = (3, img_width, img_height)
else:
    input_shape = (img_width, img_height, 3)

train_datagen = ImageDataGenerator(
rescale=1. / 255,
shear_range=0.2,
zoom_range=0.2,
horizontal_flip=True)

test_datagen = ImageDataGenerator(rescale=1. / 255)

train_generator = train_datagen.flow_from_directory(
train_data_dir,
target_size=(img_width, img_height),
batch_size=batch_size,
class_mode='categorical')

validation_generator = test_datagen.flow_from_directory(
validation_data_dir,
target_size=(img_width, img_height),
batch_size=batch_size,
class_mode='categorical')

model = Sequential()
model.add(Conv2D(16, (2, 2), input_shape=input_shape))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
 
model.add(Conv2D(16, (2, 2)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
 
model.add(Conv2D(32, (2, 2)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
 
model.add(Flatten())
model.add(Dense(32))
model.add(Activation('relu'))
model.add(Dropout(0.5))
model.add(Dense(3))
model.add(Activation('sigmoid'))


model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])


model.fit(
train_generator,
#steps_per_epoch=nb_train_samples // batch_size,
epochs=epochs,
validation_data=validation_generator,
validation_steps=nb_validation_samples // batch_size)

model.save('data/weights.h5')

print(train_generator.classes)

