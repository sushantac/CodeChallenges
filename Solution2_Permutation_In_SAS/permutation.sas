%let input_uptoNumber = 4;
%let input_setSize = 3;

data _null_;

	setSize = &input_setSize.;
	uptoNumber = &input_uptoNumber.;

	currentPointer = setSize;

	array a[&input_setSize.];
	do i = 1 to dim(a);
		a[i] = 1;
	end;

	put "----Output----";

	do i = 1 to dim(a);
		put a[i] @@;
	end;
	put ;

	continue = 1;
	if uptoNumber > 1 then do;
		do while (continue ~= 0);
			if (a[setSize] + 1) = (uptoNumber + 1) then 
				do;
					currentPointer = currentPointer - 1;
					if (currentPointer = 0) then do;
						do i = setSize to 1 by -1;
							if a[i] < uptoNumber then do;
								currentPointer = i;
								leave;
							end;
						end;
					end;

					a[currentPointer] = a[currentPointer] + 1;
					a[setSize] = 1;
					
				end;
			else 
				do;
					a[setSize] = a[setSize] + 1;
				end;


			do i = 1 to dim(a);
				put a[i] @@;
			end;
			put ;

			continue = 0;
			do i = 1 to dim(a);
				if a[i] ~= uptoNumber then do;
					continue = 1;
				end;
			end;
		end;
	end;	

	put "----Output----";

run;
