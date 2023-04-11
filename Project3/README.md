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

## Image segment samples

![17_righttop](https://user-images.githubusercontent.com/20626950/231042142-4025476f-e22b-4af2-b225-8cbd4c2e55f4.png = 100x100)
![17_righttop](https://user-images.githubusercontent.com/20626950/231042142-4025476f-e22b-4af2-b225-8cbd4c2e55f4.png = 100x100)
![17_leftbottom](https://user-images.githubusercontent.com/20626950/231042147-a4b94af0-b32d-472c-8708-c9446d387b4c.png)
![17_lefttop](https://user-images.githubusercontent.com/20626950/231042148-62fa434b-8003-4f88-b5ae-c1024b22e8a7.png)
![17_rightbottom](https://user-images.githubusercontent.com/20626950/231042151-910b0093-c64f-45bf-83b4-c35c3e5bb687.png)

