# Distributed Image Classification Application for handwritten digits

## Demo

https://user-images.githubusercontent.com/20626950/231040218-dd97ae0e-8bd7-4743-ba9d-dfc9f1568f27.mp4

## Description

The Android application developed in this project serves the purpose of capturing images of handwritten digits, dividing them into four parts, and offloading them
to four client phones for image classification. The main objective of the program is to store the image in its respective folder on the master phone based on the
majority of predicted outcomes received from the four client phones.

The application features an integrated camera that allows users to capture images of handwritten digits. The app is split into two pages, with the first page offering
the user the option to upload an image, and the second page including a button for opening the camera, taking an image, and offloading it to the client mobiles. The
offloading process entails sending image fragments to Flask servers operating on the client mobiles via the Termux apk.

Termux is a Linux terminal emulator that facilitates running Ubuntu OS on the client mobile phone, enabling the flask servers to run on the client mobile phone itself.
The flask servers running on the client phones use the pre-trained model to classify the images and provide the predicted results to the master phone. Based on the
majority of predicted outcomes received from the client mobiles, the decision is made to store the image in its respective folder. For example, if the prediction
from 3 out of 4 client mobiles for an image of digit 8 is 8 and 1 is 0, the image will be stored in folder number 8.

## Pytorch Implemention Details

The model consists of several layers, including convolutional layers, fully connected layers, and pooling layers.
The first layer is a 2D convolutional layer with an input of 3 channels (for RGB images), output of 6 channels, and a kernel size of 3x3. The second convolutional layer has an input of 6 channels, output of 16 channels, and a kernel size of 3x3. The third convolutional layer has an input of 16 channels, output of 120 channels, and a kernel size of 2x2.
After each convolutional layer, a Rectified Linear Unit (ReLU) activation function is applied to introduce non-linearity in the model. A 2D average pooling layer with a kernel size of 2x2 and stride of 2x2 is used to downsample the output of the convolutional layer. A dropout layer with a rate of 0.25 is also applied to prevent overfitting.
The output of the last convolutional layer is flattened and passed through two fully connected layers. The first fully connected layer has 120 input features and 84 output features. The second fully connected layer has 84 input features and 10 output features, which correspond to the 10 possible classes of handwritten digits (0-9).

**The image is splits into 4 parts - left top, left bottom, right top, right bottom based on its height and width and then used as a training set image.** 

## Model Accuracy

![image](https://user-images.githubusercontent.com/20626950/231043798-a0a8a692-b7a1-4323-b447-297677f0d1ab.png)

![image](https://user-images.githubusercontent.com/20626950/231044465-3d3fa261-3b94-4f6e-aa4e-a6f8f449a989.png)

