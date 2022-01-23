//
//  SongsView.swift
//  iosApp
//
//  Created by Harsewak Singh on 22/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

typealias ClickHandler<T> = (T) -> Void

struct SongView: View {
    
    var track: Track
    var clickHandler: ClickHandler<Track>?
    var body: some View {
        HStack {
            Text(track.name).onTapGesture(count: 1, perform: {
                clickHandler?(track)
            })
        }
    }
}

struct SongsView: View {
    var tracks: [Track]
    
    var body: some View {
        NavigationView {
            List(tracks, id: \.id) {
                SongView(track: $0)
            }
        }.navigationTitle(Resources.strings.songs)
    }
}

struct SongsView_Previews: PreviewProvider {
    static var previews: some View {
        SongsView(tracks: [Track(id:"0", name:"Test song", duration: 1, size: 1, state: TrackState(state: TrackState.none))])
    }
}
