/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.awt.image.BufferedImage;

/**
 *
 * @author leandrafinger
 */
public interface ImageAddable {
    public void addImage(String path);
    public boolean validateImage(BufferedImage img);
}
