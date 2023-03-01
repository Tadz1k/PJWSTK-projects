import sys

curr_word = None
curr_count = 0

for line in sys.stdin:
	line = line.strip()
	word, count = line.split('\t')
	word = word.lower()
	count = int(count)
	
	if word == curr_word:
		curr_count += count
	else:
		if curr_word:
			print('{0}\t{1}'.format(curr_word, curr_count))
		curr_word = word
		curr_count = count

if curr_word == word:
	print('{0}\t{1}'.format(curr_word, curr_count))
	
