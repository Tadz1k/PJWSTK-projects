import sys

curr_letter = None
curr_count = 0

for line in sys.stdin:
	line = line.strip()
	letter, count = line.split('\t')
	count = int(count)
	
	if letter == curr_letter:
		curr_count += count
	else:
		if curr_letter:
			print('{0}\t{1}'.format(curr_letter, curr_count))
		curr_letter = letter
		curr_count = count

if curr_letter == letter:
	print('{0}\t{1}'.format(curr_letter, curr_count))
	
