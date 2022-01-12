//
//  LibraryStore.swift
//  iosApp
//
//  Created by Harsewak Singh on 11/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//
import shared

class LibraryStore: ObservableObject, Kotlinx_coroutines_coreFlowCollector {
    
    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        guard let list = (value as? [Any?])?.compactMap({ $0 as? LibraryItem}) else {
            completionHandler(nil, NSError())
            return
        }
        self.libraryItems = list
    }
    
    
    
    let trackManager: TrackManager = TrackManagerImpl(services: ServiceManager())
    
    @Published var libraryItems: [LibraryItem] = []
    
    func start() {
//        trackManager.libraryItems
        trackManager.libraryItems.collect(collector: self, completionHandler: {
            (ku, error) in
        })
    }
}
