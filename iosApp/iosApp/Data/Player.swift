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

class Player: ObservableObject {
    
    @Published var track: Track?
    
//    var mediaPlayer: AVAudioPlayer?
    let mediaPlayer: MPMusicPlayerController? = MPMusicPlayerController.systemMusicPlayer

    
    func play(track: Track) {
//        mediaPlayer = try? AVAudioPlayer(contentsOf: URL(fileURLWithPath: track.id))
    }
    
    func play() {
//        if ((track?.state ?? nothing) != 1) {
//            mediaPlayer.start()
        track?.state = TrackState(state: TrackState.playing)
//        }
    }
    
    func pause() {
//        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        track?.state = TrackState(state: TrackState.paused)
//        }
    }
    
    func stop() {
//        if (mediaPlayer?.curre == true) {
            mediaPlayer?.stop()
            track?.state = TrackState(state: TrackState.stopped)
//        }
    }

}
