SELECT state1no, COUNT(state1no)
FROM contiguity
WHERE conttype = 1 AND state1no>state2no AND YEAR <= 1987
GROUP BY state1no