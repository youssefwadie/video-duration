package com.github.youssefwadie.videoduration.core;

import io.humble.video.Demuxer;
import io.humble.video.Global;

public class BasicScannersProvider {

    public static FileScanner videoScanner() {
        final WeightCalculator videoDurationCalculator = file -> {
            final Demuxer demuxer = Demuxer.make();
            try {
                demuxer.open(file.toAbsolutePath().toString(),
                        null,
                        false,
                        false,
                        null,
                        null);
                return demuxer.getDuration();
            } finally {
                demuxer.close();
            }
        };

        final WeightPrinter videoDurationPrinter = weight -> {
            if (weight == Global.NO_PTS) {
                return "00:00:00.00";
            }
            double duration = 1.0 * weight / Global.DEFAULT_PTS_PER_SECOND;
            int hours = (int) (duration / (60 * 60));
            int minutes = (int) ((duration - hours * 60 * 60) / 60);
            int secs = (int) (duration - hours * 60 * 60 - minutes * 60);
            int subSeconds = (int) ((duration - (hours * 60 * 60.0 + minutes * 60.0 + secs)) * 100.0);
            return String.format("%1$02d:%2$02d:%3$02d.%4$02d", hours, minutes, secs, subSeconds);
        };

        return new FileScanner(videoDurationCalculator, videoDurationPrinter);
    }

}
