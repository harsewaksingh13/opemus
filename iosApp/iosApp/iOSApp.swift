import SwiftUI
import shared

@main
struct iOSApp: App {
    @StateObject var player = Player()
    
    var body: some Scene {
        WindowGroup {
            MainView().environmentObject(player)
        }
    }
}

///MARK: KMM shared classes typealias

typealias LibraryItem = shared.LibraryItem
typealias LibraryType = shared.LibraryType
typealias TrackManager = shared.TrackManager
typealias TrackManagerImpl = shared.TrackManagerImpl
typealias ServiceManager = shared.ServiceManager
typealias Services = shared.Services
typealias Track = shared.Track

let TrackPreview = Track(id:"0", name:"Test song", duration: 1, size: 1, thumbnail: "")

///MARK: Helper typealias
typealias ClickHandler<T> = (T) -> Void


///MARK: Dependency Injection
@propertyWrapper
public struct Inject<T> {
    private var instance: T
    public init() {
        self.instance = InstanceProvider.provide(T.self)
    }
    
    public var wrappedValue: T {
        get { return instance }
    }
}

fileprivate class InstanceProvider<T> {
    
    static func provide(_ type: T.Type) -> T {
        if type == DeviceDataManager.self {
            return DeviceDataManagerImpl() as! T
        } else if type == TrackManager.self {
            return TrackManagerImpl(services: ServiceManager()) as! T
        }
        fatalError("InstanceProvider => Unrecognised type \(type) with Inject!")
    }
}

extension Image {
    
    func load(url: String?) -> Self {
        if let url = url, let uRL = URL(string: url) {
            return self.data(url: uRL)
        }
        return self.resizable()
    }
    
    func data(url: URL) -> Self {
        let data = try? Data(contentsOf: url)
        guard let data = data, let uiImage = UIImage(data: data) else {
            return self.resizable()
        }
        return Image(uiImage: uiImage).resizable()
        
    }
}


extension UIScreen{
   static let screenWidth = UIScreen.main.bounds.size.width
   static let screenHeight = UIScreen.main.bounds.size.height
   static let screenSize = UIScreen.main.bounds.size
}
