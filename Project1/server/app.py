import flask
import werkzeug
import os
import time
from flask import request

app = flask.Flask(__name__)

@app.route('/image', methods=['POST'])
def handle_request():
    if request.method == 'POST':
        imagefile = flask.request.files['image']
        imagefilename = imagefile.filename.split('.')[0] + time.strftime("%Y%m%d-%H%M%S") + '.' + imagefile.filename.split('.')[-1]
        category = flask.request.form['category']
        if not os.path.exists(category):
        	os.makedirs(category)
        os.chdir(category)
        filename = werkzeug.utils.secure_filename(imagefilename)
        print("\nReceived image File name : " + imagefilename)
        imagefile.save(filename)
        os.chdir('..')
        return "Image Uploaded Successfully"
app.run(host="0.0.0.0", port=88, debug=True)
