//
//  Player.swift
//  iosApp
//
//  Created by Harsewak Singh on 21/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import AVKit
import MediaPlayer



extension AVPlayer {
    var isPlaying: Bool {
        return timeControlStatus == .playing//rate != 0 && error == nil ||
    }
}



private class MediaPlayer  {
    
    
    lazy var audioPlayer : AVQueuePlayer = {
        return AVQueuePlayer()
    }()
    
    var isPlaying: Bool {
        return audioPlayer.isPlaying
    }
    
    private var timeObserver: Any? = nil
    
    func play(track: Track) {
        if let url = URL(string: track.id) {
            let playerItem = AVPlayerItem.init(url: url)
            audioPlayer.insert(playerItem, after: nil)
            audioPlayer.play()
            addPeriodicTimeObserver()
            
        }
    }
    
    private func addPeriodicTimeObserver() {
        if let ob = self.timeObserver {
            audioPlayer.removeTimeObserver(ob)
        }
        // Invoke callback every half second
        let interval = CMTime(seconds: 0.5,
                              preferredTimescale: CMTimeScale(NSEC_PER_SEC))
        // Add time observer
        timeObserver = audioPlayer.addPeriodicTimeObserver(forInterval: interval, queue: DispatchQueue.main) { [weak self] time in
            let currentSeconds = CMTimeGetSeconds(time)
            guard let duration = self?.audioPlayer.currentItem?.duration else { return }
            let totalSeconds = CMTimeGetSeconds(duration)
            let progress: Float = Float(currentSeconds/totalSeconds)
            print(progress)
        }
    }
    
    func play() {
        audioPlayer.play()
    }
    
    func pause() {
        audioPlayer.pause()
    }
    
    func stop() {
        audioPlayer.replaceCurrentItem(with: nil)
    }
}


class Player: ObservableObject {
    
    @Published var track: Track?
    
    var playing: Bool {
        return mediaPlayer.isPlaying
    }
    
    private let mediaPlayer: MediaPlayer = MediaPlayer()
    
    
    func play(track: Track) {
        self.track = track
        mediaPlayer.play(track: track)
    }
    
    func play() {
        if (!playing) {
            mediaPlayer.play()
        }
    }
    
    func pause() {
        if (playing) {
            mediaPlayer.pause()
        }
    }
    
    func toggle() {
        if(playing) {
            pause()
        } else {
            play()
        }
    }
    
    func stop() {
        if (playing) {
            mediaPlayer.stop()
            track = nil
        }
    }
    
}
