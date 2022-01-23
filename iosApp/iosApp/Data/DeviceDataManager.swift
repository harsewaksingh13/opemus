//
//  DeviceDataManager.swift
//  iosApp
//
//  Created by Harsewak Singh on 21/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import MediaPlayer

protocol DeviceDataManager {
    func findTracks() -> [Track]
}

extension Track {
    
    private static var mediaItemTrack: MPMediaItem? = nil
    
    var mediaItem: MPMediaItem? {
        get {
            return Track.mediaItemTrack
        }
        set(value) {
            Track.mediaItemTrack = value
        }
    }
    
    class func toTrack(_ item : MPMediaItem) -> Track {
        
        let track = Track(id: String(item.persistentID), name: item.title ?? "", duration: 1, size: 1, state: TrackState(state: TrackState.none))
        track.mediaItem = item
        return track
    }
}

let deviceDataManager: DeviceDataManager = DeviceDataManagerImpl()

class DeviceDataManagerImpl: DeviceDataManager  {


    func findTracks() -> [Track] {
        let tracksQuery = MPMediaQuery.songs()
        return tracksQuery.items?.map({ item in
            Track.toTrack(item)
        }) ?? []
    }

}
