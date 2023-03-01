
from keras.models import load_model
from keras.utils import load_img
from keras.utils import img_to_array
import numpy as np

model = load_model('data/weights.h5')

image = load_img('data/4.jpg', target_size=(150, 150))
img = np.array(image)
img = img / 255.0
img = img.reshape(1,150,150,3)
label = model.predict(img)
y_classes = label.argmax(axis=-1)

classes = {0 : 'Klawiatura', 1: 'ksiazka', 2: 'kubek'}
print(classes.get(y_classes[0]))
