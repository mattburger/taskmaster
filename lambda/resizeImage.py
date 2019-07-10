from PIL import Image

imgFile = "maint.jpg"

img1 = Image.open(imgFile)

width = 50
height = 50

img2 = img1.resize((width, height), Image.ANTIALIAS)
ext = ".jpg"

img2.save("antialias"+ext)



