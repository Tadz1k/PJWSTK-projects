import sys
import statistics
from scipy.stats import gmean

biggest_num = -sys.maxsize-1
avg_num = 0
count = 0
num_list = []
avg_geo = 0
median = 0
uniq_nums = []
geo_mul = 1

for line in sys.stdin:
	line = line.strip()
	number, count = line.split('\t')
	count = int(count)
	number = int(number)
	
	count += 1
	
	if number > biggest_num:
		biggest_num = number

	num_list.append(number)

	if number not in uniq_nums:
		uniq_nums.append(number)


#Calculate results

#avg
sum_of_nums = sum(num_list)
avg_num = sum_of_nums/len(num_list)

#geo_avg
#for i in num_list:
#	geo_mul = (geo_mul)*(i))

#geo_avg = ((geo_mul) ** (1/len(num_list))) #convert to int bcs it is long variable

geo_avg = gmean(num_list)

#median
median = statistics.median(num_list)


print(f'Max val : {biggest_num}')
print(f'Arithmetic avg : {avg_num}')
print(f'Geometric avg : {geo_avg}')
print(f'Median : {median}')
print(f'Count of unique numbers : {len(uniq_nums)}')
