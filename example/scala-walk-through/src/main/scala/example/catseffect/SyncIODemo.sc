import cats.effect.SyncIO

def putStr(str: String): SyncIO[Unit] = SyncIO.apply(println(str))

SyncIO.pure("test string").flatMap(putStr).unsafeRunSync()




