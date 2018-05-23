#Subset Sum approach to the knapsack problem

#this algorithm uses a matrix to determine if a certain value T can be made using the values in a given array of integers "nums"
#this algorithm is 1/0 because none of the numbers can be broken up to force a fit
# the run time of this algorithm is O(T*N) where T = target number and N = number of integers given
    #this is because that is how long it takes to fill in the 2d array
# in total the algorithm (with the calculation of the numbers involved) take O(T*N+N)
    #this addition of +N comes from the back tracking through the matrix
    # each iteration it is only checking the row above it
    # it is possible to find the numbers before going through each row but worst case
        #you would have to check every row (if the first number in the array is used)
def subset_sum(nums,T):
    nums.sort()
    for i in nums:
        if i > T:
            nums.remove(i)
    amt = len(nums)
    table = [[0 for x in range(T+1)]for x in range(amt)]# creates a 2d matrix with len(nums) rows and T+1 columns
    
    for i in range(0,amt):#for each row
      table[i][0] = True #make the 0 row true
      
      for j in range(1,T+1): #for each column
        if i>=1:#this will compare the row with the row above it
          if j < nums[i]:
            table[i][j] = table[i-1][j]
          else:
            if table[i-1][j-nums[i]]== True or table[i-1][j] == True:
              table[i][j] = True
            else:
              table[i][j] = False
        else:#this fills in the first row
          if j == nums[i]:
            table[i][j] = True
          else:
            table[i][j] = False
          
    if table[amt-1][T] == True:  #this is only accessible if T can be made  
      k = T
      x = amt
      included = []
      while(k>0): #this back tracks through the matrix to determine what numbers were included (the first instance that it was doable)
        if x >1:
          if table[x-1][k]== False:
            included.append(nums[x])
            k = k-nums[x]
        else:
          included.append(nums[x])
          k = k - nums[x]
        x -= 1
      included.sort()
      print True, T , "can be made using" , included
    else: #if T cannot be made
      print False, T, "cannot be made using any combination of", nums
      
          
    

nums = [1,2,4,6,8,5]
subset_sum(nums,12)#True (1,2,4,5) or (4,8) or (1,5,6)
#run time O(12*6 = 72) vs O(n^2) = 36 vs O(2^n) = 64
subset_sum(nums,26)

nums2 = [2,3,7,8,10]
subset_sum(nums2,11)#true (3,8)
subset_sum(nums2,14)#False

nums3 = [2,2,5,6]
subset_sum(nums3,9)

nums4 =[2,2,2,2]
subset_sum(nums4,6)

nums5 = [1,12]
subset_sum(nums5,11) # 12>11 so it will be removed from the list