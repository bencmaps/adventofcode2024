-- import Control.Parallel.Strategies
-- import Control.DeepSeq

blinkStone :: Integer -> [Integer]
blinkStone 0 = [1]
blinkStone stone | even (length (show stone)) = do
  let stoneStr = show stone
      (left, right) = splitAt ((length stoneStr) `div` 2) stoneStr
  [read left, read right]
blinkStone stone = [stone * 2024]

blink :: [Integer] -> [Integer]
blink stones = concatMap blinkStone stones

main :: IO ()
main = do
  input <- words <$> readFile "./input.txt"
  let stones :: [Integer] = map read input
  print stones
  print $ blink stones
  print $ length $ (iterate blink stones) !! 25
  print $ length $ (iterate blink stones) !! 75
