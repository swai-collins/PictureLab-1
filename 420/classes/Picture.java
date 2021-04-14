package classes;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 *
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments
     */
    public Picture() {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();
    }

    /**
     * Constructor that takes a file name and creates the picture
     *
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName) {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes a file name and creates the picture
     *
     * @param file the name of the file to create the picture from
     */
    public Picture(java.io.File file) {
        // let the parent class handle this fileName
        super(file);
    }

    /**
     * Constructor that takes the width and height
     * 
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int width, int height) {
        // let the parent class handle this width and height
        super(width, height);
    }

    /**
     * Constructor that takes a picture and creates a copy of that picture
     *
     * @param copyPicture the picture to copy
     */
    public Picture(SimplePicture copyPicture) {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     *
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image) {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////
    /**
     * Method to return a string with information about this picture.
     *
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString() {
        String output = "Picture, filename " + getFileName()
            + " height " + getHeight()
            + " width " + getWidth();
        return output;

    }

    /**
     * Method to create a new picture by scaling the current picture by the given
     * x and y factors
     *
     * @param xFactor the amount to scale in x
     * @param yFactor the amount to scale in y
     * @return the resulting picture
     */
    public Picture scale(double xFactor, double yFactor) {
        // set up the scale transform
        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.scale(xFactor, yFactor);

        // create a new picture object that is the right size
        Picture result = new Picture((int) (getWidth() * xFactor),
                (int) (getHeight() * yFactor));

        // get the graphics 2d object to draw on the result
        Graphics graphics = result.getGraphics();
        Graphics2D g2 = (Graphics2D) graphics;

        // draw the current image onto the result image scaled
        g2.drawImage(this.getImage(), scaleTransform, null);

        result.setTitle(getTitle());
        return result;
    }

    /**
     * Method to set the blue to 0
     */
    public void zeroBlue() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setBlue(0);
            }
        }
    }

    public void keepOnlyBlue() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setRed(0);
                pixelObj.setGreen(0);
            }
        }
    }

    public void negate() {
      Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setRed(255 - pixelObj.getRed());
                pixelObj.setGreen(255 - pixelObj.getGreen());
                pixelObj.setBlue(255 - pixelObj.getBlue());
            }
        }
    }

    public void grayscale() {
      double avg;
      Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
              avg = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3.0;
                pixelObj.setRed((int) (avg + .5));
                pixelObj.setGreen((int) (avg + .5));
                pixelObj.setBlue((int) (avg + .5));
            }
        }
    }

    public void fixUnderwater() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setRed(pixelObj.getRed() * 8);
            }
        }
    }

    /**
     * Removes all the red from this image.
     */
    public void zeroRed() {
        //TODO: Write this method.
    }

    /**
     * Removes all the green from this image.
     */
    public void zeroGreen() {
        //TODO: Write this method.   
    }

    /**
     * Method that mirrors the picture around a vertical mirror in the center of
     * the picture from left to right
     */
    public void mirrorVertical() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < width / 2; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    public void mirrorVerticalRightToLeft() {
      Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = width - 1; col > width / 2; col--) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    public void mirrorHorizontal() {
      Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = 0; row < height / 2; row++) {
                topPixel = pixels[row][col];
                bottomPixel = pixels[height - 1 - row][col];
                bottomPixel.setColor(topPixel.getColor());
            }
        }
    }

    public void mirrorHorizontalBotToTop() {
      Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = height - 1; row > height / 2; row--) {
                topPixel = pixels[row][col];
                bottomPixel = pixels[height - 1 - row][col];
                bottomPixel.setColor(topPixel.getColor());
            }
        }
    }

    public void mirrorDiagonal() {
      Pixel[][] pixels = this.getPixels2D();
      Pixel left = null;
      Pixel right = null;

      int max = pixels.length;
      if (pixels[0].length < max) {
        max = pixels[0].length;
      }

      for (int r = 0; r < max; r++) {
        for (int c = 0; c < max; c++) {
          left = pixels[r][c];
          right = pixels[c][r];
          left.setColor(right.getColor());
        }
      }
    }

    public void mirrorArms() {
      Pixel[][] pixels = this.getPixels2D();
      Pixel topPixel = null;
      Pixel bottomPixel = null;

      for (int r = 155; r < 191; r++) {
        for (int c = 98; c < 169; c++) {
          topPixel = pixels[r][c];
          bottomPixel = pixels[191 - r + 191][c];
          bottomPixel.setColor(topPixel.getColor());
        }
      }

      for (int r = 155; r < 191; r++) {
        for (int c = 238; c < 296; c++) {
          topPixel = pixels[r][c];
          bottomPixel = pixels[191 - r + 191][c];
          bottomPixel.setColor(topPixel.getColor());
        }
      }
    }

    public void createComposite() {
      Picture biden = new Picture("biden.jfif");
      Pixel[][] narPix = this.getPixels2D();
      Pixel[][] bidPix = biden.getPixels2D();
      Pixel narP = null;
      Pixel bidP = null;
      int narR = 61;
      int narC = 60;

      for (int r = 52; r < 280; r++) {
        for (int c = 102; c < 293; c++) {
          bidP = bidPix[r][c];
          narP = narPix[narR][narC];
          narP.setColor(bidP.getColor());
          narC++;
        }
        narC = 60;
        narR++;
      }
    }

    /**
     *  Creates a vertical mirror image of the this picture.
     */
    public void verticalReflection() {
        //TODO: Write this method.
    }

    /**
     * Converts this image into a horizontal mirror image of itself.
     */
    public void horizontalReflection() {
        //TODO: Write this method.
    }

    /**
     * Mirror just part of a picture of a temple
     */
    public void mirrorTemple() {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++) {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /**
     * copy from the passed fromPic to the specified startRow and startCol in the
     * current picture
     *
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, int startRow, int startCol) {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow;
        fromRow < fromPixels.length
        && toRow < toPixels.length;
        fromRow++, toRow++) {
            for (int fromCol = 0, toCol = startCol;
            fromCol < fromPixels[0].length
            && toCol < toPixels[0].length;
            fromCol++, toCol++) {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }
    }

    /**
     * 
     * @param fromPic The source image we are copying from
     * @param destRow the start row to copy to
     * @param destCol the start col to copy to
     * @param fromRow The start row of fromPic
     * @param fromCol The start col of fromPic
     * @param w Width of the area we wish to copy.
     * @param h Height of the area we wish to copy.
     */
    public void copy(Picture fromPic,int destRow, int destCol, int fromRow, int fromCol, int w, int h) {
        //TODO: Write and test this method
    }

    /**
     * Method to show large changes in color
     *
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist) {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0;
            col < pixels[0].length - 1; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col + 1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor)
                > edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

} // this } is the end of class Picture, put all new methods before this
