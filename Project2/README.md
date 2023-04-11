# Handwritten Digit Reader: An AI-powered Andriod application for image classification

## Demo

https://user-images.githubusercontent.com/20626950/231036538-140a23c2-422d-4fe3-961a-560f12c06f61.mp4

## Description
Handwritten Digit Reader is an Android application that allows users to upload images of handwritten digits to a server, where they are classified into
different classes of digits using a PyTorch model. The images are then placed into their respective folders based on their classification. The application includes
access to a camera that enables users to capture images of handwritten digits. The application consists of two pages: the first page prompts users to decide whether
they want to upload an image or not, while the second page provides a button to access the camera, take a photo, and upload it to the server.

## Installation
To install and run the application, follow these steps:

  Clone this repository to your local machine.
  
  Open the project in Android Studio.
  
  Build and run the application on an Android device or emulator.

## Usage
To use the application:

  Open the application on your Android device.
  
  On the first page, choose whether you want to upload an image or not.
  
  If you choose to upload an image, the application will take you to the second page.
  
  On the second page, click the camera button to take a photo of a handwritten digit.
  
  After taking the photo, the application will automatically classify the digit and place it into the appropriate folder.
  
## Pytorch Implementation

1. The Network class is defined as a subclass of the nn.Module class provided by PyTorch, which allows us to define our own neural network architectures.
2. The class defines the architecture of a convolutional neural network (CNN) for the purpose of classifying handwritten digits.
3. The CNN consists of three convolutional layers, two fully connected layers, and some other operations such as pooling, activation, and dropout.
4. The first convolutional layer takes a single-channel input image of size 28x28 and applies 6 filters of size 5x5 with stride 1 and no padding.
5. The second convolutional layer takes the output of the first layer, applies 16 filters of size 5x5, and applies average pooling with kernel size 2x2 and stride 2.
6. The third convolutional layer takes the output of the second layer, applies 120 filters of size 4x4, and applies ReLU activation.
7. The output of the third layer is flattened and passed through two fully connected layers, with the first layer having 120 input features and 84 output features,
and the second layer having 84 input features and 10 output features.
8. The output of the second fully connected layer is the predicted class probabilities for each of the 10 digits (0-9).

The convolutional layers perform feature extraction on the input image, and the pooling layers reduce the spatial dimensions of the output of each convolutional layer. The fully connected layers perform classification on the features extracted by the convolutional layers, and the ReLU activation function introduces nonlinearity into the network. The dropout layer is used to prevent overfitting by randomly setting a fraction of the output of the previous layer to zero during training.

## Model Accuracy
A PyTorch model is used to classify the handwritten digits, which achieved 99.66% training accuracy, 99.09% validation accuracy, and 99.19% testing accuracy.
Please refer to Fig. 1 for the accuracy of the model and Fig. 2 for a plot of epoch against loss for the training and validation data points.

![image](https://user-images.githubusercontent.com/20626950/231037096-9c08a5b6-80f9-4e85-9ba1-8d8ba35ffbc2.png)
![image](https://user-images.githubusercontent.com/20626950/231037155-38e2cf72-ca91-461d-b0e7-bdc19cc8cfe6.png)



