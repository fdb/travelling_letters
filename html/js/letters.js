var tl = tl || {};
tl.letters = {
  '?': [[0, 30], [0, 10], [10, 0], [40, 0], [50, 10], [50, 40], [40, 50], [20, 50], [20, 80], [30, 90], [20, 100], [10, 90], [20, 80]],
  '-': [[0, 60], [50, 60]],
  '\u00FC': [[0, 30], [0, 30], [0, 90], [10, 100], [50, 100], [50, 30], [30, 30], [30, 10], [30, 30], [20, 30], [20, 10]],
  '\u00F6': [[0, 90], [0, 40], [10, 30], [20, 30], [20, 10], [20, 30], [30, 30], [30, 10], [30, 30], [40, 30], [50, 40], [50, 90], [40, 100], [10, 100], [0, 90]],
  '\u00EF': [[10, 30], [10, 10], [10, 30], [30, 30], [30, 10], [30, 30], [20, 30], [20, 30], [20, 100], [40, 100], [0, 100]],
  '_': [[0, 100], [60, 100]],
  '\u00EB': [[50, 100], [10, 100], [0, 90], [0, 40], [10, 30], [18, 30], [20, 10], [20, 30], [30, 30], [30, 10], [30, 30], [40, 30], [50, 40], [50, 60], [0, 60]],
  'excl': [[10, 0], [10, 80], [0, 90], [10, 100], [20, 90], [10, 80]],
  ';': [[10, 80], [20, 90], [10, 100], [0, 90], [10, 80], [0, 70], [10, 60], [20, 70], [10, 80]],
  '\u00E8': [[50, 100], [10, 100], [0, 90], [0, 40], [10, 30], [30, 30], [20, 10], [30, 30], [40, 30], [50, 40], [50, 60], [0, 60]],
  '\u00E9': [[50, 100], [10, 100], [0, 90], [0, 40], [10, 30], [20, 30], [30, 10], [20, 30], [40, 30], [50, 40], [50, 60], [0, 60]],
  'colon': [[30, 100], [40, 90], [30, 80], [40, 70], [30, 60], [20, 70], [30, 80], [20, 90], [30, 100], [10, 120]],
  '\u00E4': [[10, 30], [10, 30], [20, 30], [20, 10], [20, 30], [30, 30], [30, 10], [30, 30], [40, 30], [50, 40], [50, 100], [10, 100], [0, 90], [0, 70], [10, 60], [50, 60]],
  '\u00E0': [[10, 30], [10, 30], [30, 30], [20, 10], [30, 30], [40, 30], [50, 40], [50, 100], [10, 100], [0, 90], [0, 70], [10, 60], [50, 60]],
  '\u00E1': [[10, 30], [10, 30], [30, 30], [40, 10], [30, 30], [40, 30], [50, 40], [50, 100], [10, 100], [0, 90], [0, 70], [10, 60], [50, 60]],
  '*': [[0, 20], [40, 60], [0, 100], [40, 60], [0, 60], [80, 60], [40, 60], [40, 20], [40, 60], [80, 20], [40, 60], [80, 100], [40, 60], [40, 100]],
  '(': [[20, 0], [0, 20], [0, 80], [20, 100]],
  '=': [[50, 60], [0, 60], [0, 80], [50, 80]],
  'z': [[0, 50], [0, 30], [50, 30], [0, 100], [50, 100], [50, 80]],
  'y': [[0, 30], [0, 90], [10, 100], [20, 100], [20, 130], [20, 100], [30, 100], [40, 90], [40, 30]],
  'x': [[0, 30], [20, 60], [40, 30], [20, 60], [0, 60], [0, 100], [0, 60], [40, 60], [40, 100]],
  'w': [[0, 30], [0, 90], [10, 100], [30, 100], [30, 60], [30, 100], [50, 100], [60, 90], [60, 30]],
  'v': [[0, 30], [0, 70], [20, 100], [40, 70], [40, 30]],
  'u': [[0, 30], [0, 90], [10, 100], [50, 100], [50, 30]],
  't': [[0, 10], [0, 30], [40, 30], [0, 30], [0, 90], [10, 100], [40, 100]],
  's': [[50, 40], [40, 30], [10, 30], [0, 40], [0, 60], [50, 60], [50, 90], [40, 100], [10, 100], [0, 90]],
  '\'': [[0, 0], [20, 30]],
  'r': [[0, 100], [0, 40], [10, 30], [40, 30], [40, 50]],
  'q': [[50, 100], [10, 100], [0, 90], [0, 40], [10, 30], [40, 30], [50, 40], [50, 130]],
  'p': [[0, 130], [0, 30], [40, 30], [50, 40], [50, 90], [40, 100], [0, 100]],
  'o': [[0, 90], [0, 40], [10, 30], [40, 30], [50, 40], [50, 90], [40, 100], [10, 100], [0, 90]],
  'n': [[0, 100], [0, 30], [30, 30], [40, 40], [40, 100]],
  'm': [[0, 100], [0, 40], [10, 30], [30, 30], [30, 70], [30, 30], [50, 30], [60, 40], [60, 100]],
  'l': [[0, 0], [20, 0], [20, 100], [40, 100]],
  'k': [[0, 0], [0, 100], [0, 60], [20, 60], [40, 30], [20, 60], [50, 60], [50, 100]],
  'j': [[30, 30], [20, 20], [30, 10], [40, 20], [30, 30], [10, 30], [30, 30], [30, 120], [20, 130], [0, 130]],
  'i': [[20, 30], [10, 20], [20, 10], [30, 20], [20, 30], [0, 30], [20, 30], [20, 100], [40, 100], [0, 100]],
  'h': [[0, 0], [0, 100], [0, 30], [40, 30], [50, 40], [50, 100]],
  'g': [[50, 100], [10, 100], [0, 90], [0, 40], [10, 30], [50, 30], [50, 100], [50, 110], [40, 120], [10, 120]],
  'f': [[40, 20], [40, 0], [20, 0], [10, 10], [10, 30], [30, 30], [10, 30], [10, 100], [0, 100], [30, 100]],
  'e': [[50, 100], [10, 100], [0, 90], [0, 40], [10, 30], [40, 30], [50, 40], [50, 60], [0, 60]],
  'd': [[50, 0], [50, 30], [50, 100], [10, 100], [0, 90], [0, 40], [10, 30], [50, 30]],
  'c': [[50, 30], [10, 30], [0, 40], [0, 90], [10, 100], [50, 100]],
  'b': [[0, 0], [0, 30], [40, 30], [50, 40], [50, 90], [40, 100], [0, 100], [0, 30]],
  'a': [[10, 30], [10, 30], [40, 30], [50, 40], [50, 100], [10, 100], [0, 90], [0, 70], [10, 60], [50, 60]],
  'Z': [[0, 30], [0, 0], [50, 0], [30, 40], [0, 100], [50, 100], [50, 70]],
  'Y': [[0, 0], [0, 40], [10, 50], [15, 50], [20, 50], [20, 100], [20, 50], [30, 50], [40, 40], [40, 0]],
  'X': [[0, 0], [20, 50], [40, 0], [20, 50], [0, 50], [0, 100], [0, 50], [40, 50], [40, 100]],
  'W': [[0, 0], [0, 50], [0, 90], [10, 100], [30, 100], [30, 50], [30, 100], [50, 100], [60, 90], [60, 50], [60, 0]],
  'V': [[0, 0], [30, 100], [60, 0]],
  'U': [[0, 0], [0, 50], [0, 90], [10, 100], [40, 100], [50, 90], [50, 50], [50, 0]],
  'T': [[30, 100], [30, 50], [30, 0], [0, 0], [0, 30], [0, 0], [60, 0], [60, 30]],
  'S': [[0, 70], [0, 90], [10, 100], [40, 100], [50, 90], [50, 70], [0, 30], [0, 10], [10, 0], [40, 0], [50, 10], [50, 30]],
  'R': [[0, 100], [0, 50], [0, 0], [40, 0], [50, 10], [50, 40], [40, 50], [0, 50], [50, 100]],
  'Q': [[0, 90], [0, 50], [0, 10], [10, 0], [40, 0], [50, 10], [50, 50], [50, 90], [40, 100], [30, 100], [30, 80], [30, 120], [30, 100], [10, 100], [0, 90]],
  '|': [[0, 0], [0, 100]],
  'P': [[0, 100], [0, 50], [0, 0], [40, 0], [50, 10], [50, 40], [40, 50], [0, 50]],
  '.': [[0, 90], [10, 80], [20, 90], [10, 100], [0, 90]],
  'O': [[0, 90], [0, 50], [0, 10], [10, 0], [40, 0], [50, 10], [50, 50], [50, 90], [40, 100], [10, 100], [0, 90]],
  'N': [[0, 100], [0, 50], [0, 0], [50, 100], [50, 50], [50, 0]],
  'M': [[0, 100], [0, 50], [0, 10], [10, 0], [30, 0], [30, 50], [30, 0], [50, 0], [60, 10], [60, 50], [60, 100]],
  'L': [[0, 0], [0, 50], [0, 100], [40, 100], [40, 70]],
  'K': [[0, 0], [0, 50], [0, 100], [0, 50], [20, 50], [50, 0], [20, 50], [50, 50], [50, 100]],
  'J': [[50, 0], [50, 50], [50, 90], [40, 100], [0, 100]],
  ' ': [[100, 100]],
  'I': [[40, 0], [0, 0], [20, 0], [20, 100], [0, 100], [40, 100]],
  ',': [[10, 100], [0, 90], [10, 80], [20, 90], [10, 100], [0, 110]],
  'H': [[0, 0], [0, 50], [0, 100], [0, 50], [50, 50], [50, 0], [50, 50], [50, 100]],
  'G': [[50, 30], [50, 10], [40, 0], [10, 0], [0, 10], [0, 90], [10, 100], [40, 100], [50, 90], [50, 50], [20, 50]],
  'F': [[0, 100], [0, 50], [30, 50], [0, 50], [0, 0], [40, 0], [40, 30]],
  'E': [[50, 30], [50, 0], [0, 0], [0, 50], [30, 50], [0, 50], [0, 100], [50, 100], [50, 70]],
  'D': [[0, 0], [40, 0], [50, 10], [50, 90], [40, 100], [0, 100], [0, 0]],
  'C': [[50, 0], [10, 0], [0, 10], [0, 50], [0, 90], [10, 100], [50, 100]],
  'at': [[50, 60], [40, 50], [20, 50], [10, 60], [10, 80], [20, 90], [40, 90], [50, 80], [50, 40], [40, 30], [10, 30], [0, 40], [0, 90], [10, 100]],
  'B': [[0, 100], [0, 50], [0, 0], [40, 0], [50, 10], [50, 50], [0, 50], [50, 50], [50, 90], [40, 100], [0, 100]],
  '"': [[20, 30], [0, 0], [20, 0], [40, 30]],
  'A': [[0, 100], [0, 10], [10, 0], [40, 0], [50, 10], [50, 50], [0, 50], [50, 50], [50, 100]],
  '>': [[0, 20], [40, 60], [0, 100]],
  '&': [[0, 100], [50, 20], [50, 10], [40, 0], [30, 0], [20, 10], [20, 20], [50, 60], [50, 90], [40, 100], [30, 100], [0, 60]],
  '9': [[50, 60], [10, 60], [0, 50], [0, 10], [10, 0], [40, 0], [50, 10], [50, 100], [0, 100]],
  '8': [[0, 50], [0, 10], [10, 0], [40, 0], [50, 10], [50, 90], [40, 100], [10, 100], [0, 90], [0, 50], [50, 50]],
  '7': [[0, 30], [0, 0], [60, 0], [20, 40], [20, 100]],
  '6': [[50, 0], [10, 0], [0, 10], [0, 30], [40, 30], [50, 40], [50, 90], [40, 100], [10, 100], [0, 90], [0, 30]],
  '5': [[50, 0], [0, 0], [0, 30], [40, 30], [50, 30], [50, 90], [40, 100], [10, 100], [0, 90], [0, 70]],
  '4': [[0, 0], [0, 50], [40, 50], [40, 0], [40, 100]],
  '3': [[0, 30], [0, 10], [10, 0], [40, 0], [50, 10], [50, 50], [20, 50], [50, 50], [50, 90], [40, 100], [10, 100], [0, 90], [0, 70]],
  '2': [[0, 30], [0, 10], [10, 0], [40, 0], [50, 10], [50, 30], [0, 70], [0, 100], [50, 100], [50, 70]],
  '1': [[0, 20], [20, 0], [20, 50], [20, 100], [0, 100], [40, 100]],
  'pound': [[20, 40], [20, 45], [20, 80], [20, 60], [0, 60], [60, 60], [40, 60], [40, 40], [40, 100], [40, 80], [60, 80], [0, 80], [20, 80], [20, 100]],
  '0': [[0, 90], [0, 50], [0, 10], [10, 0], [40, 0], [50, 10], [50, 50], [50, 90], [40, 100], [10, 100], [0, 90], [50, 10]],
  '$': [[40, 30], [30, 20], [20, 20], [20, 10], [20, 20], [10, 20], [0, 30], [0, 50], [10, 60], [20, 60], [20, 70], [20, 50], [20, 60], [30, 60], [40, 70], [40, 90], [30, 100], [20, 100], [20, 110], [20, 100], [10, 100], [0, 90]],
  ')': [[0, 0], [20, 20], [20, 80], [0, 100]],
  '<': [[40, 20], [0, 60], [40, 100]],
  '/': [[40, 0], [0, 100]],
  '+': [[30, 40], [30, 70], [60, 70], [30, 70], [30, 100], [30, 70], [0, 70]]
};