package com.nolanprice.sprite;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpriteSubscriber implements Flow.Subscriber<Set<String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpriteSubscriber.class);

    private static final int WIDTH = 832;
    private static final int HEIGHT = 1344;

    private final BufferedImage spriteSheet;
    private final CountDownLatch latch;
    private Flow.Subscription subscription;

    public SpriteSubscriber(CountDownLatch latch) {
        this.spriteSheet = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.latch = latch;
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Set<String> paths) {
        try {
            overlayPaths(paths);
            latch.countDown();
            subscription.request(1);
        } catch (IOException e) {
            LOGGER.error("Failed to overlay paths", e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Failed to process message", throwable);
    }

    @Override
    public void onComplete() {
        LOGGER.info("Finished building sprite sheet");
    }

    private void overlayPaths(Set<String> paths) throws IOException {
        if (paths.isEmpty()) {
            return;
        }
        Graphics2D g = this.spriteSheet.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,

                           RenderingHints.VALUE_ANTIALIAS_ON);
        for (String path : paths) {
            BufferedImage image = readImage(path);
            g.drawImage(image, 0, 0, null);
        }

        g.dispose();
    }

    private URI getFilePath(String fileLocation) {
        try {
            return getClass().getResource(fileLocation)
                             .toURI();
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to construct file path: %s", fileLocation), e);
            throw new RuntimeException(e);
        }
    }

    private BufferedImage readImage(String fileLocation) throws IOException {
        return ImageIO.read(new File(getFilePath(fileLocation)));
    }
}
