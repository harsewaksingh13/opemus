//
//  LibraryStore.swift
//  iosApp
//
//  Created by Harsewak Singh on 11/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//
import Foundation

class LibraryStore: ObservableObject {
        
    let trackManager: TrackManager = TrackManagerImpl(services: ServiceManager())
    
    @Published var libraryItems: [LibraryItem] = []
    
    func start() {
        trackManager.libraryItems.observe(responseHandler: { items in
            self.libraryItems = items as? [LibraryItem] ?? []
        }).errorHandler = {
            error in
            error.printStackTrace()
        }
    }
}
