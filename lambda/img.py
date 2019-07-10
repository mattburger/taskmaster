
saved = "maint_thumbnail.jpg"
size = (50, 50)

try:
    original = Image.open("maint.jpg")
    print("The size of the Image is: ")
    print(original.format, original.size, original.mode)

except:
    print("Unable to load image")

original.thumbnail(size)
original.save(saved)
original.show()