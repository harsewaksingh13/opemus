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

extension MPMusicPlayerController {
    
    var isPlaying: Bool {
        return playbackState == MPMusicPlaybackState.playing
    }
    
}



class MediaPlayer  {
    
    private lazy var deviceMediaPlayer: MPMusicPlayerController = {
        return MPMusicPlayerController.systemMusicPlayer
    }()
    
    private var audioPlayer: AVAudioPlayer?
    
    var isPlaying: Bool {
        if let audioPlayer = audioPlayer {
            return  audioPlayer.isPlaying
        } else {
            return deviceMediaPlayer.isPlaying
        }
        
    }

    func play(track: Track) {
        if let mediaItem = track.mediaItem {
            deviceMediaPlayer.nowPlayingItem = mediaItem
        } else {
            audioPlayer = try? AVAudioPlayer(contentsOf: URL(fileURLWithPath: track.id))
            audioPlayer?.play()
        }
    }
    
    func play() {
        if let audioPlayer = audioPlayer {
            audioPlayer.play()
        } else {
            deviceMediaPlayer.play()
        }
    }
    
    func pause() {
        if let audioPlayer = audioPlayer {
            audioPlayer.pause()
        } else {
            deviceMediaPlayer.pause()
        }
    }
    
    func stop() {
        if let audioPlayer = audioPlayer {
            audioPlayer.stop()
        } else {
            deviceMediaPlayer.stop()
        }
    }
}

class Player: ObservableObject {
    
    @Published var track: Track?
    
        
    let mediaPlayer: MediaPlayer = MediaPlayer()
    
    
    func play(track: Track) {
        //mediaPlayer.nowPlayingItem = track.mediaItem
        mediaPlayer.play(track: track)
    }
    
    func play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.play()
            track?.state = TrackState(state: TrackState.playing)
        }
    }
    
    func pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            track?.state = TrackState(state: TrackState.paused)
        }
    }
    
    func stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            track?.state = TrackState(state: TrackState.stopped)
        }
    }
    
}
