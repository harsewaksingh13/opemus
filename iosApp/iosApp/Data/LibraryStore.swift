//
//  LibraryStore.swift
//  iosApp
//
//  Created by Harsewak Singh on 11/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//
import Foundation

class LibraryStore: ObservableObject {
      
    @Inject
    var trackManager: TrackManager
    
    @Published var libraryItems: [LibraryItem] = []
    
    @Published var songs: [Track] = []
    
    func start() {
        trackManager.libraryItems.observe(responseHandler: { items in
            self.libraryItems = items as? [LibraryItem] ?? []
        }).errorHandler = {
            error in
            error.printStackTrace()
        }
        trackManager.recentTracks.observe(responseHandler: { items in
            self.songs = items as? [Track] ?? []
        }).errorHandler = {
            error in
            error.printStackTrace()
        }
        trackManager.fetchTracks()
    }
}
