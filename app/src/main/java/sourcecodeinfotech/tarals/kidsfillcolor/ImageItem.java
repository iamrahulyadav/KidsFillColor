package sourcecodeinfotech.tarals.kidsfillcolor;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private int id;

    public ImageItem(Bitmap image, int id) {
        super();
        this.image = image;
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getTitle() {
        return id;
    }

    public void setTitle(String title) {
        this.id = id;
    }
}
