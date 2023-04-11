import flask
import werkzeug
import os
import time
from flask import request
from PIL import Image, ImageOps
from torch import nn
import torchvision.transforms as T
import torch
import cv2
import numpy as np

app = flask.Flask(__name__)

class Network(nn.Module):
    def __init__(self):
        super().__init__()

        self.conv1 = nn.Conv2d(in_channels=1, out_channels=6, kernel_size=(5, 5), stride=(1, 1), padding=(0, 0))
        self.conv2 = nn.Conv2d(in_channels=6, out_channels=16, kernel_size=(5, 5), stride=(1, 1), padding=(0, 0))
        self.conv3 = nn.Conv2d(in_channels=16, out_channels=120, kernel_size=(4, 4), stride=(1, 1), padding=(0, 0))

        self.fully_connected1 = nn.Linear(in_features=120, out_features=84)
        self.fully_connected2 = nn.Linear(in_features=84, out_features=10)

        self.pooling_layer = nn.AvgPool2d(kernel_size=(2, 2), stride=(2, 2))
        self.relu = nn.ReLU()
        self.dropout = nn.Dropout(0.25)

    def forward(self, x):
        # Convolution Layer 1
        x = self.conv1(x)
        x = self.relu(x)
        x = self.pooling_layer(x)

        # Convolution Layer 2
        x = self.conv2(x)
        x = self.relu(x)
        x = self.pooling_layer(x)
        x = self.dropout(x)

        # Convolution Layer 3
        x = self.conv3(x)
        x = self.relu(x)

        # flatten x
        x = x.view(-1, 120)

        # Fully connected layer 1
        x = self.fully_connected1(x)
        x = self.relu(x)

        # Fully connected layer 2
        x = self.fully_connected2(x)

        return x

@app.route('/image', methods=['POST'])
def handle_request():
    if request.method == 'POST':
        imagefile = flask.request.files['image']
        img = Image.open(imagefile) # load with Pillow
        img_copy = cv2.cvtColor(np.array(img), cv2.COLOR_BGR2GRAY)
        img = cv2.cvtColor(np.array(img), cv2.COLOR_BGR2GRAY)
        transform = T.Compose([
            T.ToTensor(),
            T.Resize((28, 28))
        ])
        img = transform(img)
        model = Network()
        model.load_state_dict(torch.load('mnist_model.pth', map_location=torch.device('cpu')))
        model.eval()
        results = model(img)
        print(results)
        category = torch.argmax(results)
        print(category)
        category = str(category.item())
        imagefilename = imagefile.filename.split('.')[0] + time.strftime("%Y%m%d-%H%M%S") + '.' + imagefile.filename.split('.')[-1]
        if not os.path.exists(category):
            os.makedirs(category)
        os.chdir(category)
        filename = werkzeug.utils.secure_filename(imagefilename)
        print("\nReceived image File name : " + imagefilename)
        cv2.imwrite(filename, img_copy)
        os.chdir('..')
        return "Image Uploaded Successfully"
app.run(host="0.0.0.0", port=88, debug=True)
