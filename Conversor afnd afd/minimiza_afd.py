
def find_set(parent, e):
    if parent[e] != e:
        parent[e] = find_set(parent, parent[e])
    return parent[e]

def union(parent, rank, e1, e2):
    root1, root2 = find_set(parent, e1), find_set(parent, e2)
    if root1 != root2:
        if rank[root1] > rank[root2]:
            parent[root2] = root1
        else:
            parent[root1] = root2
            if rank[root1] == rank[root2]:
                rank[root2] += 1

def get_sets(parent):
    groups = {}
    for e in parent:
        root = find_set(parent, e)
        if root not in groups:
            groups[root] = set()
        groups[root].add(e)
    return list(groups.values())

def minimize_dfa(filename):
    with open(filename, "r") as f:
        lines = f.read().strip().split("\n")
    
    states = lines[0].split()
    start_state = lines[1].strip()
    final_states = set(lines[2].split())
    transitions = {}
    terminals = set()
    
    for line in lines[3:]:
        state, symbol, next_state = line.split()
        transitions[(state, symbol)] = next_state
        terminals.add(symbol)
    
    def order_tuple(a, b):
        return (a, b) if a < b else (b, a)
    
    table = {}
    sorted_states = sorted(states)
    
    for i, s1 in enumerate(sorted_states):
        for s2 in sorted_states[i+1:]:
            table[(s1, s2)] = (s1 in final_states) != (s2 in final_states)
    
    flag = True
    while flag:
        flag = False
        for i, s1 in enumerate(sorted_states):
            for s2 in sorted_states[i+1:]:
                if table[(s1, s2)]:
                    continue
                for w in terminals:
                    t1 = transitions.get((s1, w))
                    t2 = transitions.get((s2, w))
                    if t1 is not None and t2 is not None and t1 != t2:
                        marked = table[order_tuple(t1, t2)]
                        flag = flag or marked
                        table[(s1, s2)] = marked
                        if marked:
                            break
    
    parent = {s: s for s in states}
    rank = {s: 0 for s in states}
    
    for (s1, s2), marked in table.items():
        if not marked:
            union(parent, rank, s1, s2)
    
    state_groups = get_sets(parent)
    new_states = [str(i + 1) for i in range(len(state_groups))]
    state_map = {s: str(i + 1) for i, group in enumerate(state_groups) for s in group}
    
    start_state = state_map[start_state]
    final_states = {state_map[s] for s in final_states}
    
    new_transitions = {(state_map[k[0]], k[1]): state_map[v] for k, v in transitions.items()}
    
    


    with open("output/saidaMin.txt", "w") as f:
        f.write(" ".join(["Q" + state for state in new_states]) + "\n")  
        f.write("Q" + start_state + "\n")  
        f.write(" ".join(["Q" + state for state in final_states]) + "\n")  
        for (state, symbol), next_state in new_transitions.items():
            f.write(f"Q{state} {symbol} Q{next_state}\n")  




