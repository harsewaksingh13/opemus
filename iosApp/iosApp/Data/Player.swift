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




extension TrackState {
    static let playing: Int32 = TrackState.companion.playing
    static let paused: Int32 = TrackState.companion.paused
    static let stopped: Int32 = TrackState.companion.stopped
    static let none: Int32 = TrackState.companion.none
}

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
  
    func play(track: Track) {
        if let url = URL(string: track.id) {
            let playerItem = AVPlayerItem.init(url: url)
            audioPlayer.insert(playerItem, after: nil)
            audioPlayer.play()
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
            track?.state = TrackState(state: TrackState.playing)
        }
    }
    
    func pause() {
        if (playing) {
            mediaPlayer.pause()
            track?.state = TrackState(state: TrackState.paused)
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
