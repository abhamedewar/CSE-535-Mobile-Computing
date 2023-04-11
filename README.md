# CSE 535-Mobile Computing

## Project 1: Image Categorizer Application

Image Categorizer App is an Android application that allows users to store their clicked images on a server, categorized based on the category they select. The application is integrated with a camera that enables users to capture images of their choice. Users can select a category for the clicked image from the category dropdown menu provided in the application. The application consists of two pages: the first page prompts users to decide whetherthey want to click a picture, while the second page provides a button to access the camera, take a photo,select a category for the captured image and upload it to the server.

## Project 2: Handwritten Digit Reader: An AI-powered Andriod application for image classification

Handwritten Digit Reader is an Android application that allows users to upload images of handwritten digits to a server, where they are classified into different classes of digits using a PyTorch model. The images are then placed into their respective folders based on their classification. The application includes access to a camera that enables users to capture images of handwritten digits. The application consists of two pages: the first page prompts users to decide whether they want to upload an image or not, while the second page provides a button to access the camera, take a photo, and upload it to the server.

## Project 3: Distributed Image Classification Application for Handwritten Digits

The Android application developed in this project serves the purpose of capturing images of handwritten digits, dividing them into four parts, and offloading them to four client phones for image classification. The main objective of the program is to store the image in its respective folder on the master phone based on the majority of predicted outcomes received from the four client phones.

The application features an integrated camera that allows users to capture images of handwritten digits. The app is split into two pages, with the first page offering the user the option to upload an image, and the second page including a button for opening the camera, taking an image, and offloading it to the client mobiles. The offloading process entails sending image fragments to Flask servers operating on the client mobiles via the Termux apk.

Termux is a Linux terminal emulator that facilitates running Ubuntu OS on the client mobile phone, enabling the flask servers to run on the client mobile phone itself. The flask servers running on the client phones use the pre-trained model to classify the images and provide the predicted results to the master phone. Based on the majority of predicted outcomes received from the client mobiles, the decision is made to store the image in its respective folder. For example, if the prediction from 3 out of 4 client mobiles for an image of digit 8 is 8 and 1 is 0, the image will be stored in folder number 8.

## Project 1 Demo

https://user-images.githubusercontent.com/20626950/231031832-daa9a670-35fc-40a4-9223-6bdcce61eace.mp4

## Project 2 Demo

https://user-images.githubusercontent.com/20626950/231036538-140a23c2-422d-4fe3-961a-560f12c06f61.mp4

## Project 3 Demo

https://user-images.githubusercontent.com/20626950/231040218-dd97ae0e-8bd7-4743-ba9d-dfc9f1568f27.mp4
