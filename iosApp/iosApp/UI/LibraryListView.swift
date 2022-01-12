//
//  LibraryListView.swift
//  iosApp
//
//  Created by Harsewak Singh on 11/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct LibraryListView: View {
    
   let items: [LibraryItem]
    
    var body: some View {
        List(items, id: \.id) {
            LibraryItemView(library: $0)
        }
    }
}

struct LibraryListView_Previews: PreviewProvider {
    static var previews: some View {
        LibraryListView(items: LibraryPreviewItems)
    }
}
