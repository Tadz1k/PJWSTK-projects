import numpy as np
import cv2
from operator import itemgetter
  
#Najpierw trenuję deskryptor, aby znał piłę z różnych stron
query_imgs = []
query_img = cv2.imread('data\\saw1.jpg')
query_imgs.append(query_img)
query_img2 = cv2.imread('data\\saw2.jpg')
query_imgs.append(query_img2)
query_img3 = cv2.imread('data\\saw3.jpg')
query_imgs.append(query_img3)
query_img4 = cv2.imread('data\\saw4.jpg')
query_imgs.append(query_img4)

query_img_bw = cv2.cvtColor(query_img,cv2.COLOR_BGR2GRAY)
query_img_bw2 = cv2.cvtColor(query_img2,cv2.COLOR_BGR2GRAY)
query_img_bw3 = cv2.cvtColor(query_img3,cv2.COLOR_BGR2GRAY)
query_img_bw4 = cv2.cvtColor(query_img4,cv2.COLOR_BGR2GRAY)
  
#Trenowanie
orb = cv2.ORB_create(250)
query_keypoints = []
query_descriptors = []

queryKeypoints, queryDescriptors = orb.detectAndCompute(query_img_bw,None)
queryKeypoints2, queryDescriptors2 = orb.detectAndCompute(query_img_bw2,None)
queryKeypoints3, queryDescriptors3 = orb.detectAndCompute(query_img_bw3,None)
queryKeypoints4, queryDescriptors4 = orb.detectAndCompute(query_img_bw4,None)

query_keypoints.append(queryKeypoints)
query_keypoints.append(queryKeypoints2)
query_keypoints.append(queryKeypoints3)
query_keypoints.append(queryKeypoints4)

query_descriptors.append(queryDescriptors)
query_descriptors.append(queryDescriptors2)
query_descriptors.append(queryDescriptors3)
query_descriptors.append(queryDescriptors4)

#Ładuję obrazek
video = cv2.VideoCapture('data\\sawmovie.mp4')
capture, image = video.read()

while capture:
    capture, image = video.read()
    #Obrazek porównywany
    train_img_bw = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    #Tutaj trenuję obecny obrazek do testowania
    trainKeypoints, trainDescriptors = orb.detectAndCompute(train_img_bw,None)
 
    matcher = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=True)#HAMMING
    #Teraz spsrawdzimy matche w pętli
    max_matches = 0
    best_match = 0
    best_matches = []
    for i in range (len(query_descriptors)):
        matches = matcher.match(query_descriptors[i],trainDescriptors)
        matches = sorted(matches, key = lambda x:x.distance)
        if len(matches) > max_matches:
            max_matches = len(matches)
            best_match = i
            best_matches = matches
    
    matches = [match.trainIdx for match in matches[:5]]
    matched_keypoints = np.float32([keypoint.pt for keypoint in itemgetter(*matches)(trainKeypoints)])

    #final_img = cv2.drawMatches(image,trainKeypoints,query_imgs[best_match],query_keypoints[best_match],best_matches[:10],None,flags=cv2.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS)
    final_img = cv2.drawKeypoints(image, trainKeypoints, None)
    rect = cv2.boundingRect(matched_keypoints)
    cv2.rectangle(final_img, rect, [0,0,255])
    final_img = cv2.resize(final_img, (1000,650))
    second_img = cv2.drawKeypoints(query_imgs[best_match], query_keypoints[best_match], None)
    second_img = cv2.resize(second_img, (1000, 650))

    cv2.imshow("Original", second_img)
    cv2.imshow("Matches", final_img)
    cv2.waitKey(1)