Handwritten Digit Classifier App

## Description
Handwritten Digit Classifier App is an Android application that allows users to upload images of handwritten digits to a server, where they are classified into
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

## Model Accuracy
We created a PyTorch model to classify the handwritten digits, which achieved 99.66% training accuracy, 99.09% validation accuracy, and 99.19% testing accuracy.
Please refer to Fig. 1 for the accuracy of the model and Fig. 2 for a plot of epoch against loss for the training and validation data points.

![image](https://user-images.githubusercontent.com/20626950/231037096-9c08a5b6-80f9-4e85-9ba1-8d8ba35ffbc2.png)
![image](https://user-images.githubusercontent.com/20626950/231037155-38e2cf72-ca91-461d-b0e7-bdc19cc8cfe6.png)

## Demo

https://user-images.githubusercontent.com/20626950/231036538-140a23c2-422d-4fe3-961a-560f12c06f61.mp4

